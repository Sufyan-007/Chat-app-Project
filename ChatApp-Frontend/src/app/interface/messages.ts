import { Time } from "@angular/common";
import { User } from "./user";

export interface Messages {
    self: boolean;
    conversationId: number;
    message:string,
    media: boolean,
    sender:User,
    sentAt: number,
    updatedAt: number
}
