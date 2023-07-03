import { Component, OnDestroy } from "@angular/core";
import { Subscription } from "rxjs";

@Component({ template: '' })
export abstract class BaseComponent implements OnDestroy {
	subscriptions: Subscription[] = []

	ngOnDestroy(): void {
		this.subscriptions.forEach(sub => sub.unsubscribe())
	}
}