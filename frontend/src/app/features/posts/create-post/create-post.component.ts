import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Topic } from '../../../core/models/topic/topic.model';
import { TopicService } from '../../../core/services/topic.service';
import { PostService } from '../../../core/services/post.service';
import { CreatePostRequest } from '../../../core/models/post/create-post-request.model';
import { BackButton } from '../../../layout/back-button/back-button';

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, BackButton],
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.scss',
})
export class CreatePostComponent implements OnInit {
  private topicService = inject(TopicService);
  private postService = inject(PostService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  topics = signal<Topic[]>([]);
  isLoadingTopics = signal<boolean>(true);
  isSubmitting = signal<boolean>(false);
  errorMessage = signal<string>('');

  postForm = this.fb.nonNullable.group({
    topicId: ['', [Validators.required]],
    title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
    content: ['', [Validators.required, Validators.minLength(10)]],
  });

  ngOnInit(): void {
    this.loadTopics();
  }

  private loadTopics(): void {
    this.isLoadingTopics.set(true);
    this.topicService.getTopics().subscribe({
      next: (topics: Topic[]) => {
        this.topics.set(topics);
        this.isLoadingTopics.set(false);
      },
      error: (error: Error) => {
        this.errorMessage.set('Erreur lors du chargement des thèmes.');
        this.isLoadingTopics.set(false);
        console.error('Error loading topics:', error);
      },
    });
  }

  onSubmit(): void {
    if (this.postForm.invalid) {
      this.postForm.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set('');

    const formValue = this.postForm.value;
    const request: CreatePostRequest = {
      topicId: Number(formValue.topicId),
      title: formValue.title!,
      content: formValue.content!,
    };

    this.postService.createPost(request).subscribe({
      next: (createdPost) => {
        this.isSubmitting.set(false);
        // Navigate to the created post or to feed
        this.router.navigate(['/posts', createdPost.id]);
      },
      error: (error: Error) => {
        this.errorMessage.set("Erreur lors de la création de l'article. Veuillez réessayer.");
        this.isSubmitting.set(false);
        console.error('Error creating post:', error);
      },
    });
  }

  get topicIdControl() {
    return this.postForm.get('topicId');
  }

  get titleControl() {
    return this.postForm.get('title');
  }

  get contentControl() {
    return this.postForm.get('content');
  }
}
