import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-error-state',
  templateUrl: './error-state.component.html',
  styleUrls: ['./error-state.component.scss'],
  standalone: true,
  imports: [CommonModule],
})
export class ErrorStateComponent {
  @Input() message = 'Une erreur est survenue';
  @Input() showRetryButton = false;
  @Input() retryButtonLabel = 'Réessayer';
  @Output() retry = new EventEmitter<void>();

  onRetry(): void {
    this.retry.emit();
  }
}
