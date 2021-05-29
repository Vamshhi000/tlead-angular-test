import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminGuardGuard } from './admin-guard.guard';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { AdminLoginComponent } from './admin/admin-login/admin-login.component';
import { AuthGuard } from './auth.guard';
import { ChatComponent } from './chat/chat.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { HomeScreenComponent } from './home-screen/home-screen.component';
import { PaidUsersGuardGuard } from './paid-users-guard.guard';
import { PaymentGatewayComponent } from './payment-gateway/payment-gateway.component';
import { ResetGuardGuard } from './reset-guard.guard';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserRegisterComponent } from './user-register/user-register.component';
import { ViewReferalsComponent } from './view-referals/view-referals.component';

const routes: Routes = [

 

{path:'LoginComponent',component:UserLoginComponent},
{path:"RegisterComponent",component:UserRegisterComponent},
{path:"forgotPassword",component:ForgotPasswordComponent},


{path : '' , redirectTo : '/RegisterComponent' , pathMatch : 'full'},
{path:'AdminLogin/Ij3UsKr1gL4dsZKcZ5FXwJNYh3o0IjURP5DmUeQh0Zrl6',component:AdminLoginComponent},




{
  path: '',
  canActivate: [AuthGuard],
  children: [

    {path:"paymentComponent",component:PaymentGatewayComponent},

   
  ]
},{



  path: '',
  canActivate: [ResetGuardGuard],
  children: [

    {path:"resetPassword",component:ResetPasswordComponent},

   
  ]
},
{
  path: '',
  canActivate: [PaidUsersGuardGuard],
  children: [

    {path:"homeScreen",component:HomeScreenComponent},
    {path:"viewReferals",component:ViewReferalsComponent},
    {path:"chatService",component:ChatComponent}
  ]
},
{
  path: '',
  canActivate: [AdminGuardGuard],
  children: [

    {path:"adminHome",component:AdminHomeComponent}

  ]
}

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
