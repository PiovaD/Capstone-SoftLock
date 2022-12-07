import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/login/login.module').then(m => m.LoginModule)
  },
  {
    path: 'registration',
    loadChildren: () => import('./pages/registration/registration.module').then(m => m.RegistrationModule)
  },
  {
    path: 'profile',
    loadChildren: () => import('./pages/user-profile/user-profile.module').then(m => m.UserProfileModule)
  },
  {
    path: 'profile-edit',
    loadChildren: () => import('./pages/edit-profile/edit-profile.module').then(m => m.EditProfileModule)
  },
  {
    path: 'game',
    loadChildren: () => import('./pages/game/game.module').then(m => m.GameModule)
  },
  {
    path: 'post',
    loadChildren: () => import('./pages/post/post.module').then(m => m.PostModule)
  },
  {
    path: 'reviews',
    loadChildren: () => import('./pages/reviews/reviews.module').then(m => m.ReviewsModule)
  },
  {
    path: 'questions',
    loadChildren: () => import('./pages/questions/questions.module').then(m => m.QuestionsModule)
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
