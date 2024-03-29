package by.epam.courierPicker.type;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.command.impl.*;

public enum CommandType {

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },

    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },

    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },

    COURIER_OFFER {
        {
            this.command = new CourierOfferCommand();
        }
    },

    SHOW_APPROVED_COURIER_OFFERS {
        {
            this.command = new ShowApprovedCourierOffersCommand();
        }
    },

    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },

    DELETE_COURIER_OFFER {
        {
            this.command = new DeleteCourierOfferCommand();
        }
    },

    DELETE_USER_OFFER {
        {
            this.command = new DeleteUserOfferCommand();
        }
    },

    SEND_USER_OFFER {
        {
            this.command = new SendUserOfferCommand();
        }
    },

    ACCEPT_USER_OFFER {
        {
            this.command = new AcceptUserOfferCommand();
        }
    },

    ACCEPT_COURIER_OFFER {
        {
            this.command = new AcceptCourierOfferCommand();
        }
    },

    HOME_COMMAND {
        {
            this.command = new HomeCommand();
        }
    },

    ACCOUNT_COMMAND {
        {
            this.command = new AccountCommand();
        }
    },

    FINISH_COURIER_OFFER {
        {
            this.command = new FinishCourierOfferCommand();
        }
    },

    USER_OFFER {
        {
            this.command = new UserOfferCommand();
        }
    },

    APPROVE_COURIER_OFFER {
        {
            this.command = new ApproveCourierOfferCommand();
        }
    },

    APPROVE_USER_OFFER {
        {
            this.command = new ApproveUserOfferCommand();
        }
    },

    SHOW_APPROVED_USER_OFFERS {
        {
            this.command = new ShowApprovedUserOffersCommand();
        }
    },

    SEND_COURIER_OFFER {
        {
            this.command = new SendCourierOfferCommand();
        }
    },

    FINISH_USER_OFFER {
        {
            this.command = new FinishUserOfferCommand();
        }
    },

    KICK_COURIER_FROM_OFFER {
        {
            this.command = new KickCourierFromOfferCommand();
        }
    },

    EDIT_COMMAND {
        {
            this.command = new EditCommand();
        }
    },

    UPDATE_PROFILE_COMMAND {
        {
            this.command = new UpdateProfileCommand();
        }
    },

    BLOCK_USER {
        {
            this.command = new BlockUserCommand();
        }
    },

    UNBLOCK_USER {
        {
            this.command = new UnblockUserCommand();
        }
    },

    ADD_ADMIN {
        {
            this.command = new AddAdminCommand();
        }
    },

    FIND_COURIER_OFFERS_BY_PARAMETERS {
        {
            this.command = new FindCourierOffersByParametersCommand();
        }
    },

    FIND_USER_OFFERS_BY_PARAMETERS {
        {
            this.command = new FindUserOffersByParametersCommand();
        }
    }

    ;

    ActionCommand command;

    public ActionCommand getCommand() {
        return command;
    }

}
