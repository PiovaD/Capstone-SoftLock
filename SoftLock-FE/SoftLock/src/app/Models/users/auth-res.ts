export interface UserAuthRes {
  token: string;
  id: number;
  username: string;
  email: string;
  roles: string[];
  profilePicUrl: string | null;
}
