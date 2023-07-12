package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.joinmessage;


import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.JoinMessageCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.guis.SelectJoinMessageGUI;

public class CmdChangeJoinMessage extends JoinMessageCommands {

    private static final CmdChangeJoinMessage i = new CmdChangeJoinMessage();
    public static CmdChangeJoinMessage get() { return i; }

    public CmdChangeJoinMessage() {
        this.setAliases("changejoinmessage");
        this.setDesc("Change your join message");
    }

    @Override
    public void perform(){
        new SelectJoinMessageGUI(me).open();
    }
}
