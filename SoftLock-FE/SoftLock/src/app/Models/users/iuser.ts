import { IRole } from "./irole";

export interface IUser {
  id: number;
  name: string;
  lastName: string;
  username: string;
  email: string;
  password?: string;
  profilePicUrl: string | null;
  roles: IRole[];
}
