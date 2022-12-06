import { IGame } from "../games/igame"
import { IUser } from "../users/iuser"

export interface IPost {
  id: number,
  date: Date,
  user: IUser,
  game: IGame,
  title: string,
  titleSlug: string,
  text: string,
  upVote: IUser[],
  downVote: IUser[]
}
