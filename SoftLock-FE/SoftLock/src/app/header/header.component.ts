import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { AuthService } from '../Services/auth.service';
import { UserAuthRes } from '../Models/users/auth-res';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  isLogged: boolean = false;
  user?: UserAuthRes | null;

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
      return 'MyProfile';
    }
  }

  logout(): void {
    this.authService.removeAccess();
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/game', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl])
    });
  }

}
