import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../core/services/user.service';
import { SubscriptionService } from '../../core/services/subscription.service';
import { UserProfile } from '../../core/models/user/user-profile.model';
import { UpdateProfileRequest } from '../../core/models/user/update-profile-request.model';
import { passwordValidator } from '../auth/validators/password.validator';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { StatusMessagesComponent } from '../../core/components/status-messages/status-messages.component';
import { finalize } from 'rxjs';
import { PasswordRequirements } from '../../layout/password-requirements/password-requirements';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PasswordRequirements, StatusMessagesComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent implements OnInit {
  private userService = inject(UserService);
  private subscriptionService = inject(SubscriptionService);
  private destroyRef = inject(DestroyRef);
  private fb = inject(FormBuilder);
  protected router = inject(Router);

  // Signals for reactive state
  profile = signal<UserProfile | null>(null);
  isLoading = signal<boolean>(true);
  isEditMode = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);
  errorMessage = signal<string>('');
  successMessage = signal<string>('');

  // Form for editing profile
  profileForm!: FormGroup;

  ngOnInit(): void {
    this.initForm();
    this.loadProfile();
  }

  private initForm(): void {
    this.profileForm = this.fb.group({
      username: [''],
      email: ['', [Validators.email]],
      password: ['', [passwordValidator()]],
    });
  }

  private loadProfile(): void {
    this.isLoading.set(true);
    this.errorMessage.set('');

    this.userService
      .getUserProfile()
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => this.isLoading.set(false))
      )
      .subscribe({
        next: (profile: UserProfile) => {
          this.profile.set(profile);
          // Populate form with current profile data
          this.profileForm.patchValue({
            username: profile.name,
            email: profile.email,
          });
        },
        error: (error) => {
          this.errorMessage.set('Erreur lors du chargement du profil. Veuillez réessayer.');
        },
      });
  }

  /**
   * Save profile changes
   */
  onSaveProfile(): void {
    if (this.isSubmitting()) {
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set('');
    this.successMessage.set('');

    // Build update request (only include non-empty fields)
    const formValue = this.profileForm.value;
    const updates: UpdateProfileRequest = {
      username:
        formValue.username && formValue.username !== this.profile()?.name
          ? formValue.username
          : undefined,
      email:
        formValue.email && formValue.email !== this.profile()?.email ? formValue.email : undefined,
      password: formValue.password || undefined,
    };

    // If no changes, exit edit mode
    if (this.isUpdatesEmpty(updates)) {
      this.isEditMode.set(false);
      this.isSubmitting.set(false);
      return;
    }

    this.userService
      .updateProfile(updates)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => this.isSubmitting.set(false))
      )
      .subscribe({
        next: (updatedProfile: UserProfile) => {
          this.profile.set(updatedProfile);
          this.isEditMode.set(false);
          this.successMessage.set('Profil mis à jour avec succès !');
          this.profileForm.get('password')?.reset();
        },
        error: (error) => {
          console.log(error);
          if (error.error.detail === 'Email already exists') {
            this.errorMessage.set('Cet email est déjà utilisé.');
          } else {
            this.errorMessage.set('Erreur lors de la mise à jour du profil. Veuillez réessayer.');
          }
        },
      });
  }

  /** Check if the updates object has no changes */
  private isUpdatesEmpty(updates: UpdateProfileRequest): boolean {
    return Object.values(updates).every((value) => value === undefined);
  }

  /** Unsubscribe from a topic */
  onUnsubscribe(topicId: number): void {
    this.subscriptionService
      .unsubscribe(topicId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          // Remove topic from subscriptions list
          const currentProfile = this.profile();
          if (currentProfile) {
            const updatedSubscriptions = currentProfile.subscriptions.filter(
              (topic) => topic.id !== topicId
            );
            this.profile.set({
              ...currentProfile,
              subscriptions: updatedSubscriptions,
            });
            this.successMessage.set('Désabonnement réussi !');
          }
        },
        error: (error) => {
          this.errorMessage.set('Erreur lors du désabonnement. Veuillez réessayer.');
        },
      });
  }

  // Form control getters for template
  get email() {
    return this.profileForm.get('email');
  }

  get username() {
    return this.profileForm.get('username');
  }

  get password() {
    return this.profileForm.get('password');
  }

  get passwordErrors(): ValidationErrors {
    const value = this.password?.value || '';

    // Calculer l'état de chaque critère en temps réel
    return {
      hasMinLength: value.length >= 8,
      hasUpperCase: /[A-Z]/.test(value),
      hasLowerCase: /[a-z]/.test(value),
      hasNumeric: /[0-9]/.test(value),
      hasSpecialChar: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(value),
    };
  }
}
