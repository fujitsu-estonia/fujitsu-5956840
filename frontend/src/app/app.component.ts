import { Component } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  sidenavOpen = false;

  constructor(
    private matIconRegistry: MatIconRegistry,
    private domSanitzer: DomSanitizer,
  ) {
    this.loadCustomSVGIcons();
  }

  openSidenav() {
    this.sidenavOpen = true;
  }

  setOpenFalse() {
    this.sidenavOpen = false;
  }


  loadCustomSVGIcons(): void {
    //custom icons
    this.matIconRegistry.addSvgIcon(
      'veera_burger',
      this.domSanitzer.bypassSecurityTrustResourceUrl(
        'assets/icons/burger.svg'
      )
    );

    this.matIconRegistry.addSvgIcon(
      'veera_login',
      this.domSanitzer.bypassSecurityTrustResourceUrl(
        'assets/icons/login.svg'
      )
    );

    this.matIconRegistry.addSvgIcon(
      'veera_bookings',
      this.domSanitzer.bypassSecurityTrustResourceUrl(
        'assets/icons/bookings.svg'
      )
    );

    this.matIconRegistry.addSvgIcon(
      'veera_forward',
      this.domSanitzer.bypassSecurityTrustResourceUrl(
        'assets/icons/arrow_forward.svg'
      )
    );
  }
}
