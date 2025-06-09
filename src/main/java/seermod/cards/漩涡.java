package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import seermod.character.SEER;
import seermod.powers.冻伤;
import seermod.util.CardStats;

public class 漩涡 extends BaseCard{
    public static final String ID = makeID("Swirl");
    private static final int BASE_EXHAUSTIVE = 1;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.STATUS, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public 漩涡() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(2, 1);
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new 冻伤(m, p, magicNumber), magicNumber));
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }
}