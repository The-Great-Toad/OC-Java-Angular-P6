import { Comment } from '../comment/comment.model';
import { Topic } from '../topic/topic.model';

export interface Post {
  id: number;
  topicId: number;
  topic: Topic;
  title: string;
  content: string;
  author: string;
  createdAt: string;
  comments: Comment[];
}
