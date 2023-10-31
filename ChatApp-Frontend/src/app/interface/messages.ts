import { Time } from "@angular/common";
import { User } from "./user";

export interface Messages {
    self: boolean;
    message:String,
    media: boolean,
    sender:User,
    sentAt: number,
    updatedAt: Time
}
