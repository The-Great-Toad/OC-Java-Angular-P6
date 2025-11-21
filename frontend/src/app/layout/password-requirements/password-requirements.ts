import { Component, input, signal } from '@angular/core';
import { ValidationErrors } from '@angular/forms';

@Component({
  selector: 'app-password-requirements',
  imports: [],
  templateUrl: './password-requirements.html',
  styleUrl: './password-requirements.scss',
})
export class PasswordRequirements {
  public passwordErrors = input<any>({});
}
