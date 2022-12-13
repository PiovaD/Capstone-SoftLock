import { IPost } from "./ipost";

export interface IReview extends IPost {
  vote: number
}
