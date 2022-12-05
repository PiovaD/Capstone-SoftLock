import { IGenre } from "./igenre";
import { IPlatform } from "./iplatform";

export interface IGame {
  id: number;
  igdbID: number;
  name: string;
  slug: string;
  summary: string;
  imageID: string;
  releaseDate: Date;
  genres: IGenre[];
  platforms: IPlatform[];
}
