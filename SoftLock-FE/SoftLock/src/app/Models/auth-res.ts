export interface AuthRes {
  accessToken: string;
  id: number;
  username: string;
  email: string;
  profilePicUrl: string | null;
}
