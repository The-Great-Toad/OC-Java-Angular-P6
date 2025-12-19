import { Pipe, PipeTransform } from '@angular/core';

/**
 * Pipe pour formater une date en format français (jj/mm/aaaa)
 *
 * @example
 * {{ dateString | frenchDate }}
 * '2024-12-19' => '19/12/2024'
 */
@Pipe({
  name: 'frenchDate',
  standalone: true,
})
export class FrenchDatePipe implements PipeTransform {
  transform(dateString: string): string {
    if (!dateString) {
      return '';
    }

    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  }
}
