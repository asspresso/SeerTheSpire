package seermod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import seermod.cards.谱尼真身元素;
import seermod.cards.谱尼真身圣洁;
import java.util.ArrayList;
import static seermod.SeerMod.makeID;

public class 谱尼封印 extends BasePower {
    private AbstractCreature source;
    public static final String POWER_ID = makeID("Spell");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public 谱尼封印(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (amount >= 7) {
            ArrayList<AbstractCard> stanceChoices = new ArrayList();
            stanceChoices.add(new 谱尼真身元素());
            stanceChoices.add(new 谱尼真身圣洁());
            addToBot(new ChooseOneAction(stanceChoices));
            addToTop(new RemoveSpecificPowerAction(owner, owner, 谱尼封印.POWER_ID));
            if (amount - 7 > 0) {
                addToBot(new DrawCardAction(owner, amount - 7));
            }
        }

    }
}
