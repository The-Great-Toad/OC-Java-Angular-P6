import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Post } from '../models/post/post.model';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/posts`;

  /**
   * Get all posts for the authenticated user's feed
   * Posts are from topics the user is subscribed to
   */
  getUserFeed(): Observable<Post[]> {
    return this.http.get<Post[]>(`${environment.apiUrl}/feed`);
  }

  /** Get a single post by ID */
  getPost(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`);
  }
}
