package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import seermod.character.SEER;
import seermod.util.CardStats;

public class 锁魂灯 extends BaseCard{
    public static final String ID = makeID("Soul Trapper");
    private static final int BASE_EXHAUSTIVE = 5;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public 锁魂灯() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalBlock = m.currentBlock;
        addToBot(new RemoveAllBlockAction(m, p));
        if (totalBlock <= 8 && upgraded) {
            addToBot(new GainBlockAction(p, p, totalBlock * 3));
        } else {
            addToBot(new GainBlockAction(p, p, totalBlock * 2));
        }
        addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 1), 1));
    }
}

