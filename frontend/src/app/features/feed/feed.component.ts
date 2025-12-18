import { Component, inject, signal, OnInit, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { PostService } from '../../core/services/post.service';
import { Post } from '../../core/models/post/post.model';
import { finalize } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss',
})
export class FeedComponent implements OnInit {
  private readonly postService = inject(PostService);
  private readonly destroyRef = inject(DestroyRef);
  protected readonly router = inject(Router);

  protected posts = signal<Post[]>([]);
  protected isPostsEmpty = signal<boolean>(false);
  protected isLoading = signal<boolean>(true);
  protected errorMessage = signal<string>('');
  protected sortOrder = signal<'asc' | 'desc'>('desc'); // desc = plus récent en premier

  ngOnInit(): void {
    this.getUserFeed();
  }

  /** Get user feed posts */
  public getUserFeed(): void {
    this.isLoading.set(true);
    this.errorMessage.set('');

    this.postService
      .getUserFeed()
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => this.isLoading.set(false))
      )
      .subscribe({
        next: (posts: Post[]) => {
          this.isPostsEmpty.set(posts.length === 0);
          if (!this.isPostsEmpty()) {
            this.posts.set(this.sortPosts(posts));
          }
        },
        error: (error: Error) => {
          console.error('Error loading posts:', error);
          this.errorMessage.set('Erreur lors du chargement des articles');
        },
      });
  }

  /** Toggle the sort order between ascending and descending */
  public toggleSortOrder(): void {
    const newOrder = this.sortOrder() === 'desc' ? 'asc' : 'desc';
    this.sortOrder.set(newOrder);
    this.posts.update((posts) => this.sortPosts([...posts]));
  }

  /** Sort posts based on the current sort order */
  private sortPosts(posts: Post[]): Post[] {
    return posts.sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return this.sortOrder() === 'desc' ? dateB - dateA : dateA - dateB;
    });
  }

  /** Format a date string to a localized French date */
  public formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  }

  /** Navigate to the detailed view of a post */
  public viewPost(postId: number): void {
    this.router.navigate(['/posts', postId]);
  }

  /** Navigate to the post creation page */
  public createPost(): void {
    this.router.navigate(['/posts/create']);
  }
}
