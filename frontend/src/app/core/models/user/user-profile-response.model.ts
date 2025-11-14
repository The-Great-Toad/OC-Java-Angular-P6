import { Subscription } from '../subscription/subscription.model';

export interface UserProfileResponse {
  id: string; // UUID
  email: string;
  username: string;
  subscriptions: Subscription[];
}
