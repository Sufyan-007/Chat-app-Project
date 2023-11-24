import { User } from "./user";

export interface Conversation {
    id:number;
    groupChat:boolean;
    users:User[]
    latestMessage:string;
    conversationName:string;
    description:string;
    iconUrl:string;
}
