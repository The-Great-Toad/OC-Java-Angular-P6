import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validateur personnalisé pour le mot de passe
 * Exigences :
 * - Minimum 8 caractères
 * - Au moins 1 majuscule
 * - Au moins 1 minuscule
 * - Au moins 1 chiffre
 * - Au moins 1 caractère spécial
 */
export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return null;
    }

    const hasMinLength = value.length >= 8;
    const hasUpperCase = /[A-Z]/.test(value);
    const hasLowerCase = /[a-z]/.test(value);
    const hasNumeric = /[0-9]/.test(value);
    const hasSpecialChar = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(value);

    const passwordValid =
      hasMinLength && hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar;

    return !passwordValid
      ? {
          passwordStrength: {
            hasMinLength,
            hasUpperCase,
            hasLowerCase,
            hasNumeric,
            hasSpecialChar,
          },
        }
      : null;
  };
}
