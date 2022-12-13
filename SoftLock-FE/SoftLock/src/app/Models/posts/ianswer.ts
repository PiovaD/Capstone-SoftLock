import { IPost } from "./ipost";

export interface IAnswer extends IPost {
  question: IPost
}
