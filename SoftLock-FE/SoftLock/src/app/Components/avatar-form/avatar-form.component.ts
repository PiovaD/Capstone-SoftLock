import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { PrimeNGConfig } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-avatar-form',
  templateUrl: './avatar-form.component.html',
  styleUrls: ['./avatar-form.component.scss'],
  providers: [DialogService]
})
export class AvatarFormComponent implements OnInit {

  @Input() avatarForm!: FormGroup;

  gender: string | null = null;
  avatars: { nopic: null, male: string[], female: string[] } = {
    male: [
      "jack",
      "jed",
      "jai",
      "jerry",
      "jean",
      "joe",
      "josh",
      "jordan",
      "jude",
      "james",
      "jon",
      "jake",
      "jacques",
      "jia"
    ],
    female: [
      "jane",
      "jess",
      "jolee",
      "jodi",
      "jana",
      "jabala",
      "jenni",
      "jazebelle",
      "jeri",
      "jeane",
      "jocelyn",
      "julie",
      "josephine",
      "jaqueline"
    ],
    nopic: null
  }

  displayModal?: boolean;

  constructor(private primengConfig: PrimeNGConfig) { }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
  }

  show() {
    this.displayModal = true;
  }

  avatarSelect(gender: string | null, avatar: string) {
    this.displayModal = false;
    this.avatarForm.controls["profilePicUrl"].markAsDirty();
    this.avatarForm.controls["profilePicUrl"].setValue('assets/Schmoes/' + gender + '/' + avatar + '.svg');
  }

  reset() {
    this.displayModal = false;
    this.avatarForm.controls["profilePicUrl"].markAsDirty();
    this.avatarForm.controls["profilePicUrl"].setValue(null);
  }

}
