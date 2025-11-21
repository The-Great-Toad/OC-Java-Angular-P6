import { Component, DestroyRef, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { RegisterRequest } from '../../../core/models/auth/register-request.model';
import { passwordValidator } from '../validators/password.validator';
import { BackButton } from '../../../layout/back-button/back-button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { finalize } from 'rxjs';
import { PasswordRequirements } from '../../../layout/password-requirements/password-requirements';

@Component({
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, BackButton, PasswordRequirements],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly destroyRef = inject(DestroyRef);
  private readonly router = inject(Router);

  errorMessage = signal<string | null>(null);
  isLoading = signal<boolean>(false);

  registerForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    username: ['', [Validators.required]],
    password: ['', [Validators.required, passwordValidator()]],
  });

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);

    const registerRequest: RegisterRequest = {
      email: this.registerForm.value.email!,
      name: this.registerForm.value.username!,
      password: this.registerForm.value.password!,
    };

    this.authService
      .register(registerRequest)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => this.isLoading.set(false))
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/feed']);
        },
        error: (error) => {
          this.isLoading.set(false);
          if (error.status === 400) {
            this.errorMessage.set('Cet email est déjà utilisé');
          } else {
            this.errorMessage.set('Une erreur est survenue. Veuillez réessayer.');
          }
        },
      });
  }

  get email() {
    return this.registerForm.get('email');
  }

  get username() {
    return this.registerForm.get('username');
  }

  get password() {
    return this.registerForm.get('password');
  }

  get passwordErrors(): any {
    return this.password?.errors?.['passwordStrength'];
  }
}
