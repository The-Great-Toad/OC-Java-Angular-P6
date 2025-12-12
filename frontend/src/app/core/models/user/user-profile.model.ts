import { Topic } from '../topic/topic.model';

export interface UserProfile {
  email: string;
  name: string;
  subscriptions: Topic[];
}
