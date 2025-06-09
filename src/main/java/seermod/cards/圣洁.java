package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import seermod.character.SEER;
import seermod.powers.*;
import seermod.util.CardStats;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class 圣洁 extends BaseCard {
    public static final String ID = makeID("Sacredness");
    private static final int BASE_EXHAUSTIVE = -2;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    public 圣洁() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(1, 1);
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (player.hasPower(谱尼元素.POWER_ID) || player.hasPower(谱尼圣洁.POWER_ID)) {
            addToBot(new DrawCardAction(p, 2));
        } else if (player.hasPower(混沌魔域.POWER_ID)){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2), 2));
        } else {
            addToBot(new ApplyPowerAction(p, p, new 谱尼封印(p, p, 2), 2));
        }

        if (player.hasPower(致命一击.POWER_ID)) {
            addToBot(new GainEnergyAction(magicNumber));
        } else {
            addToBot(new ApplyPowerAction(p, p, new 致命一击(p, 1), 1));
        }

    }
}