import { RoleType } from "./role-type";

export interface IUser {
  id: number;
  name: string;
  lastName: string;
  username: string;
  email: string;
  password?: string;
  profilePicUrl: string | null;
  roles: { id: number, roleType: RoleType }[];
}

