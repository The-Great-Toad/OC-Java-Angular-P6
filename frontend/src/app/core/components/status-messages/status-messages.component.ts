import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingStateComponent } from '../loading-state/loading-state.component';
import { ErrorStateComponent } from '../error-state/error-state.component';
import { SuccessStateComponent } from '../success-state/success-state.component';

@Component({
  selector: 'app-status-messages',
  templateUrl: './status-messages.component.html',
  styleUrls: ['./status-messages.component.scss'],
  standalone: true,
  imports: [CommonModule, LoadingStateComponent, ErrorStateComponent, SuccessStateComponent],
})
export class StatusMessagesComponent {
  @Input() isLoading = false;
  @Input() loadingMessage = 'Chargement...';

  @Input() errorMessage: string | null = null;
  @Input() showRetryButton = false;
  @Input() retryButtonLabel = 'Réessayer';
  @Output() retry = new EventEmitter<void>();

  @Input() successMessage: string | null = null;

  onRetry(): void {
    this.retry.emit();
  }
}
