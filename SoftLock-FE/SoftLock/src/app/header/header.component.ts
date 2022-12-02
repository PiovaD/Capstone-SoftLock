import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { AuthService } from '../Auth/auth.service';
import { AuthRes } from '../Models/auth-res';
import { IUser } from '../Models/iuser';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  isLogged: boolean = false;
  user?: AuthRes | null;

  constructor(private primengConfig: PrimeNGConfig, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.primengConfig.ripple = true

    this.getUserName()


  }

  getUserName(): string {
    this.user = this.authService.getLoggedUser();

    if (this.user) {
      this.isLogged = true;
      return this.user.username;
    } else {
      this.isLogged = false;
      return 'Profile';
    }
  }

  logout(){
    this.authService.removeAccess();
    this.router.navigate(['/']);
  }

}
