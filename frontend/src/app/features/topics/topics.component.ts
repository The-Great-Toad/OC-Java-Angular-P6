import { CommonModule } from '@angular/common';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { finalize, forkJoin } from 'rxjs';
import { Topic } from '../../core/models/topic/topic.model';
import { TopicService } from '../../core/services/topic.service';
import { SubscriptionService } from '../../core/services/subscription.service';
import { UserService } from '../../core/services/user.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss',
})
export class TopicsComponent implements OnInit {
  private topicService = inject(TopicService);
  private subscriptionService = inject(SubscriptionService);
  private userService = inject(UserService);
  private readonly destroyRef = inject(DestroyRef);

  topics = signal<Topic[]>([]);
  subscribedTopicIds = signal<Set<number>>(new Set());
  isLoading = signal<boolean>(true);
  errorMessage = signal<string>('');

  ngOnInit(): void {
    this.loadTopics();
  }

  /** Load topics and user subscriptions */
  private loadTopics(): void {
    this.isLoading.set(true);
    this.errorMessage.set('');

    // Fetch both topics and user profile in parallel
    forkJoin({
      topics: this.topicService.getTopics(),
      profile: this.userService.getUserProfile(),
    })
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => this.isLoading.set(false))
      )
      .subscribe({
        next: ({ topics, profile }) => {
          const orderedTopics = this.sortUnsubscribedFirst(topics, profile.subscriptions);
          this.topics.set(orderedTopics);

          // Extract subscribed topic IDs from user profile
          const subscribedIds = new Set<number>(
            profile.subscriptions.map((topic: Topic) => topic.id)
          );
          this.subscribedTopicIds.set(subscribedIds);
        },
        error: (error: Error) => {
          console.error('Error loading topics:', error);
          this.errorMessage.set('Erreur lors du chargement des thèmes. Veuillez réessayer.');
        },
      });
  }

  /** Check if the user is subscribed to a topic */
  protected isSubscribed(topicId: number): boolean {
    return this.subscribedTopicIds().has(topicId);
  }

  /** Subscribe to a topic */
  protected subscribe(topicId: number): void {
    this.subscriptionService
      .subscribe(topicId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          const updatedSet = new Set(this.subscribedTopicIds());
          updatedSet.add(topicId);
          this.subscribedTopicIds.set(updatedSet);
        },
        error: (error: Error) => {
          console.error('Error subscribing to topic:', error);
          this.errorMessage.set("Erreur lors de l'abonnement au thème. Veuillez réessayer.");
        },
      });
  }

  /** Retry loading topics in case of error */
  protected retryLoad(): void {
    this.loadTopics();
  }

  /** Sort topics to have unsubscribed topics first */
  private sortUnsubscribedFirst(topics: Topic[], subscriptions: Topic[]): Topic[] {
    const subscribedIds = new Set(subscriptions.map((topic) => topic.id));
    return topics.sort((a, b) => {
      const aSubscribed = subscribedIds.has(a.id) ? 1 : 0;
      const bSubscribed = subscribedIds.has(b.id) ? 1 : 0;
      return aSubscribed - bSubscribed;
    });
  }
}
