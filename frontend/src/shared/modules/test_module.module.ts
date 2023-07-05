import { HttpClientTestingModule } from "@angular/common/http/testing";
import { MatDialogModule } from "@angular/material/dialog";
import { MatIconTestingModule } from "@angular/material/icon/testing";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { RouterTestingModule } from "@angular/router/testing";

export const test_module = [HttpClientTestingModule, MatDialogModule, MatSnackBarModule, MatIconTestingModule, RouterTestingModule]