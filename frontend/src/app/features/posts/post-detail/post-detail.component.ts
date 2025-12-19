import { CommonModule } from '@angular/common';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '../../../core/models/post/post.model';
import { Comment } from '../../../core/models/comment/comment.model';
import { PostService } from '../../../core/services/post.service';
import { CommentService } from '../../../core/services/comment.service';
import { BackButton } from '../../../layout/back-button/back-button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { finalize } from 'rxjs';
import { LoadingStateComponent } from '../../../core/components/loading-state/loading-state.component';
import { ErrorStateComponent } from '../../../core/components/error-state/error-state.component';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    BackButton,
    LoadingStateComponent,
    ErrorStateComponent,
  ],
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.scss',
})
export class PostDetailComponent implements OnInit {
  private postService = inject(PostService);
  private commentService = inject(CommentService);
  private readonly destroyRef = inject(DestroyRef);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);
  protected router = inject(Router);

  post = signal<Post | null>(null);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string>('');
  isSubmitting = signal<boolean>(false);

  commentForm = this.fb.nonNullable.group({
    content: ['', [Validators.required, Validators.minLength(3)]],
  });

  ngOnInit(): void {
    const postId = Number(this.route.snapshot.paramMap.get('id'));
    if (postId) {
      this.loadPost(postId);
    } else {
      this.errorMessage.set("ID d'article invalide");
      this.isLoading.set(false);
    }
  }

  private loadPost(id: number): void {
    this.isLoading.set(true);
    this.errorMessage.set('');

    this.postService
      .getPost(id)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => this.isLoading.set(false))
      )
      .subscribe({
        next: (post: Post) => {
          this.post.set(post);
        },
        error: (error: Error) => {
          this.errorMessage.set("Erreur lors du chargement de l'article. Veuillez réessayer.");
        },
      });
  }

  protected onSubmitComment(): void {
    if (this.commentForm.invalid || !this.post()) {
      return;
    }

    this.isSubmitting.set(true);
    const content = this.commentForm.value.content!;
    const postId = this.post()!.id;

    this.commentService
      .createComment(postId, content)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => this.isSubmitting.set(false))
      )
      .subscribe({
        next: (newComment: Comment) => {
          // Add the new comment to the post
          const currentPost = this.post()!;
          const updatedPost = {
            ...currentPost,
            comments: [...currentPost.comments, newComment],
          };
          this.post.set(updatedPost);

          // Reset form
          this.commentForm.reset();
        },
        error: (error: Error) => {
          this.errorMessage.set("Erreur lors de l'ajout du commentaire. Veuillez réessayer.");
        },
      });
  }

  protected goBack(): void {
    this.router.navigate(['/feed']);
  }

  protected formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  }

  get contentControl() {
    return this.commentForm.get('content');
  }
}
