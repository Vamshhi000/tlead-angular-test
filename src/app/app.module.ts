import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserRegisterComponent } from './user-register/user-register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import { UserLoginComponent } from './user-login/user-login.component';
import {MatDialogModule} from '@angular/material/dialog';
import { OtpVerificationComponent } from './otp-verification/otp-verification.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { NumberVerificatioNComponent } from './number-verificatio-n/number-verificatio-n.component';
import { AuthInterceptor } from './auth.interceptor';
import { PaymentGatewayComponent } from './payment-gateway/payment-gateway.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { NavBarMainComponent } from './nav-bar-main/nav-bar-main.component';
import {MatIconModule} from '@angular/material/icon';
import { HomeScreenComponent } from './home-screen/home-screen.component';
import { SharerMethod } from 'ngx-sharebuttons';
import { ShareButtonsModule } from 'ngx-sharebuttons/buttons';
import { ShareIconsModule } from 'ngx-sharebuttons/icons';
import { ViewReferalsComponent } from './view-referals/view-referals.component';
import { ChatComponent } from './chat/chat.component';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import { ChatRequestAndSearchComponent } from './chat-request-and-search/chat-request-and-search.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import { AdminLoginComponent } from './admin/admin-login/admin-login.component';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { ViewDataComponent } from './admin/view-data/view-data.component';
import { EditUsersComponent } from './admin/edit-users/edit-users.component';
import { AddUsersComponent } from './admin/add-users/add-users.component';
import { DeleteUsersComponent } from './admin/delete-users/delete-users.component';

@NgModule({
  declarations: [
    AppComponent,
    UserRegisterComponent,
    UserLoginComponent,
    OtpVerificationComponent,
    NumberVerificatioNComponent,
    PaymentGatewayComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    NavBarMainComponent,
    HomeScreenComponent,
    ViewReferalsComponent,
    ChatComponent,
    ChatRequestAndSearchComponent,
    AdminLoginComponent,
    AdminHomeComponent,
    ViewDataComponent,
    EditUsersComponent,
    AddUsersComponent,
    DeleteUsersComponent,

    
  ],
  imports: [ 
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatProgressBarModule,
    HttpClientModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatIconModule,
    ShareButtonsModule.withConfig({
      debug: true,
      sharerMethod: SharerMethod.Window
    }),
    ShareIconsModule,
    MatBottomSheetModule,
    MatSidenavModule
 
  ],
  providers: [{provide:HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
// [{provide:HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true}]