import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/Auth/auth.service';
import { ValidatorService } from 'src/app/Models/validator.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
  providers: [MessageService]
})
export class RegistrationComponent implements OnInit {

  registerForm!: FormGroup;
  isLoading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private validationService: ValidatorService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      name: [null, [Validators.required, Validators.minLength(3)]],
      lastName: [null, [Validators.required, Validators.minLength(3)]],
      username: [
        null,
        [Validators.required, Validators.pattern('[\\S]{3,}$')],
        this.validationService.usernameValidator,
      ],
      email: [
        null,
        [Validators.email, Validators.required],
        this.validationService.emailValidator,
      ],
      password: [null, [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\S]{5,}$')]],
      confirm: ['', [this.confirmValidator]],
      profilePicUrl: [null]
    });

  }

  submitForm(): void {
    this.isLoading = true;
    this.authService.register(this.registerForm.value)
      .subscribe({
        complete: () => this.router.navigate(['/login']),
        error: (err) => {
          console.error('httpError', err);
          this.isLoading = false;
          err.status == 0 ?
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Server error' })
            :
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Registration failed check the data' });

          setTimeout(
            () => {
              this.messageService.clear()
            }, 3000);
        }
      });
  }

  resetForm(): void {
    this.registerForm.reset();
  }

  confirmValidator = (
    control: FormControl
  ): { [s: string]: boolean } | null => {
    if (control.value && control.value != this.registerForm.value.password) {
      return { error: true };
    }
    return null;
  };


}
