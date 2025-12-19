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

    const hasMinLength = value ? value.length >= 8 : false;
    const hasUpperCase = value ? /[A-Z]/.test(value) : false;
    const hasLowerCase = value ? /[a-z]/.test(value) : false;
    const hasNumeric = value ? /[0-9]/.test(value) : false;
    const hasSpecialChar = value ? /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(value) : false;

    const passwordValid =
      hasMinLength && hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar;

    // Toujours retourner l'état des critères pour affichage en temps réel
    // mais ne marquer comme invalide que si le mot de passe ne respecte pas tous les critères
    return passwordValid
      ? null // Mot de passe valide, pas d'erreur
      : {
          passwordStrength: {
            hasMinLength,
            hasUpperCase,
            hasLowerCase,
            hasNumeric,
            hasSpecialChar,
          },
        };
  };
}
