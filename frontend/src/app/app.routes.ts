import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { unauthGuard } from './core/guards/unauth.guard';

export const routes: Routes = [
  // Unauthenticated routes
  {
    path: '',
    title: 'MDD - Accueil',
    canActivate: [unauthGuard],
    loadComponent: () =>
      import('./features/auth/landing/landing.component').then((m) => m.LandingComponent),
  },
  {
    path: 'login',
    title: 'MDD - Connexion',
    canActivate: [unauthGuard],
    loadComponent: () =>
      import('./features/auth/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    title: 'MDD - Inscription',
    canActivate: [unauthGuard],
    loadComponent: () =>
      import('./features/auth/register/register.component').then((m) => m.RegisterComponent),
  },

  // Authenticated routes
  {
    path: 'feed',
    title: "MDD - Fil d'actualité",
    canActivate: [authGuard],
    loadComponent: () => import('./features/feed/feed.component').then((m) => m.FeedComponent),
  },
  {
    path: 'topics',
    title: 'MDD - Thèmes',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/topics/topics.component').then((m) => m.TopicsComponent),
  },
  {
    path: 'posts/create',
    title: 'MDD - Créer un article',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/posts/create-post/create-post.component').then(
        (m) => m.CreatePostComponent
      ),
  },
  {
    path: 'posts/:id',
    title: 'MDD - Article',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/posts/post-detail/post-detail.component').then(
        (m) => m.PostDetailComponent
      ),
  },
  {
    path: 'profile',
    title: 'MDD - Mon profil',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/profile/profile.component').then((m) => m.ProfileComponent),
  },
  {
    path: '**',
    redirectTo: '',
  },
];
