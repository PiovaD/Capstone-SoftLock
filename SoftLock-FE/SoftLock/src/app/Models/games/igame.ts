import { IGenre } from "./igenre";
import { IPlatform } from "./iplatform";

export interface IGame {
  id: number;
  igdbID: number;
  name: string;
  slug: string;
  summary: string;
  imageID: string;
  relaseDate: Date;
  generes: IGenre[];
  platforms: IPlatform[];
}
