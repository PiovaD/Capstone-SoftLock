import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss'],
  providers: [MessageService]
})
export class UserFormComponent implements OnInit {

  @Input() userForm!: FormGroup;

  constructor() { }

  ngOnInit(): void {


  }

}
