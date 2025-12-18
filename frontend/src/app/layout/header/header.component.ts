import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  // Mobile menu state
  isMobileMenuOpen = signal<boolean>(false);

  protected isAuthenticated() {
    return this.authService.isAuthenticated();
  }

  protected isProfilePage(): boolean {
    return this.router.url === '/profile';
  }

  protected toggleMobileMenu(): void {
    this.isMobileMenuOpen.update((value: boolean) => !value);
  }

  protected closeMobileMenu(): void {
    this.isMobileMenuOpen.set(false);
  }

  protected logout(): void {
    this.authService.logout();
    this.closeMobileMenu();
  }
}
