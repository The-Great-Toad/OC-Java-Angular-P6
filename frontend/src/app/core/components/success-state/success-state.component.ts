import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-success-state',
  templateUrl: './success-state.component.html',
  styleUrls: ['./success-state.component.scss'],
  standalone: true,
})
export class SuccessStateComponent {
  @Input() message = 'Opération réussie';
}
