import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./login/login.component";
import { PagenotfoundComponent } from "./pagenotfound/pagenotfound.component";
import { HomePageComponent } from "./home-page/home-page.component";
import { homeGuard } from "./home.guard";
import { ProfileComponent } from "./home-page/profile/profile.component";
import { SignupComponent } from "./login/signup/signup.component";
import { SettingsComponent } from "./home-page/settings/settings.component";
import { SearchComponent } from "./search/search.component";
import { AddPostComponent } from "./add-post/add-post.component";
import { PostDetailsComponent } from "./home-page/post-details/post-details.component";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import {FeedComponent} from "./feed/feed.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent},
  { path: 'testing', component: ProfileComponent },
  { path: 'home' , component: HomePageComponent,
    canActivate: [homeGuard], children:[
      { path:'feed' , component: FeedComponent},
      { path: 'profile', component: ProfileComponent },
      { path: 'settings', component: SettingsComponent },
      { path: 'profile/:id/:username', component: ProfileComponent },
      { path: 'post/:id', component: PostDetailsComponent },
      { path: 'search', component: SearchComponent },
      { path: 'addpost', component: AddPostComponent }
    ]
  },
  { path: '**', component: PagenotfoundComponent }
]
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  exports:[
    RouterModule
  ]

})
export class AppRoutingModule {}
