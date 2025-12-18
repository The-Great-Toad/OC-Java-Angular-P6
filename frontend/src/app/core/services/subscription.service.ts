import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Subscription } from '../models/subscription/subscription.model';

@Injectable({
  providedIn: 'root',
})
export class SubscriptionService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/subscriptions`;

  /**
   * Subscribe to a topic
   * @param topicId - The ID of the topic to subscribe to
   */
  subscribe(topicId: number): Observable<Subscription> {
    return this.http.post<Subscription>(`${this.apiUrl}/${topicId}/subscribe`, {});
  }

  /**
   * Unsubscribe from a topic
   * @param topicId - The ID of the topic to unsubscribe from
   */
  unsubscribe(topicId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${topicId}/unsubscribe`);
  }
}
