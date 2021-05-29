import {FormControl, Validators} from '@angular/forms'
import { ValidatorFn, AbstractControl } from '@angular/forms';
import { FormGroup } from '@angular/forms';


export class Customvalidators implements Validators{

    // static emailValidator(control:FormControl){
    //   const email:String=control.value;
    //   const index:number=email.lastIndexOf('@');
    //   const domain:String=email.substring(index+1,email.length);

    //   const invalidemail=false;
    // //   console.log(domain!=='gmail.com'||'@yahoo.com'||'@outlook.com');
      
    //   if(domain!=='gmail.com'){
    //      if(domain!=='yahoo.com'){
    //          if(domain!=='outlook.com'){
    //             return{
    //                 invalidemail:true
    //              }
    //          }
    //      }
    //   }

    //   return invalidemail;
    // }

    static phoneNumberValidator(control:FormControl){
let invalidNumber=false;
      if(isNaN(Number(control.value))){

return{

  invalidNumber:true,
 
}

      }
return invalidNumber;
    }



   static patternValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } => {
          if (!control.value) {
            return null;
          }
          const regex = new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$');
          const valid = regex.test(control.value);
          return valid ? null : { invalidPassword: true };
        };
      }
    
    static MatchPassword(password: string, reenterPassword: string) {
        return (formGroup: FormGroup) => {
          const passwordControl = formGroup.controls[password];
          const confirmPasswordControl = formGroup.controls[reenterPassword];
    
          if (!passwordControl || !confirmPasswordControl) {
            return null;
          }
    
          if (confirmPasswordControl.errors && !confirmPasswordControl.errors.passwordMismatch) {
            return null;
          }
    
          if (passwordControl.value !== confirmPasswordControl.value) {
            confirmPasswordControl.setErrors({ passwordMismatch: true });
          } else {
            confirmPasswordControl.setErrors(null);
          }
        }
      }
    



}










