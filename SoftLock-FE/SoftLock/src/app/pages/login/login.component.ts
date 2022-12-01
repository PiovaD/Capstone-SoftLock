import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Message, MessageService, PrimeNGConfig } from 'primeng/api';
import { AuthService } from 'src/app/Auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [MessageService]
})
export class LoginComponent implements OnInit {
  validateForm!: FormGroup;
  isLoading: boolean = false;

  msgs1: Message[] | undefined;

  constructor(
    private primengConfig: PrimeNGConfig,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.primengConfig.ripple = true

    this.validateForm = this.formBuilder.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [false, [Validators.required]],
    });
  }

  submitForm(): void {
     if (this.validateForm.valid) {
       this.isLoading = true;
      this.authService.login(this.validateForm.value).subscribe({
         next: (res) => this.authService.saveAccess(res, this.validateForm.value.remember),
         complete: () => this.router.navigate(['/']),
         error: (err) => {
           console.error(this.validateForm.value);
           console.error('httpError', err.error);
           this.isLoading = false;
           this.messageService.add({severity:'error', summary:'Error', detail:'Login failed check the data'});
          },
       });
     } else {
      Object.values(this.validateForm.controls).forEach((control) => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
     }
  }
}

