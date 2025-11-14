import { Comment } from '../comment/comment.model';

export interface Post {
  id: number;
  topicId: number;
  topicName: string;
  title: string;
  content: string;
  author: string;
  createdAt: string;
  comments: Comment[];
}
