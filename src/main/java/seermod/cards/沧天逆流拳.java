package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import seermod.character.SEER;
import seermod.powers.中毒;
import seermod.powers.冻伤;
import seermod.powers.烧伤;
import seermod.util.CardStats;

public class 沧天逆流拳 extends BaseCard{
    public static final String ID = makeID("Aqua Pummel");
    private static final int BASE_EXHAUSTIVE = 5;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public 沧天逆流拳() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(4, 2);
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    private int countTotalDebuffs() {
        int debuffCount = 0;
        String[] powerIDs = {冻伤.POWER_ID, 烧伤.POWER_ID, 中毒.POWER_ID};

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            for (String powerID : powerIDs) {
                if (monster.hasPower(powerID)) {
                    AbstractPower power = monster.getPower(powerID);
                    debuffCount += power.amount;
                }
            }
        }
        debuffCount /= 2;
        return debuffCount;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        magicNumber = countTotalDebuffs();
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new BlizzardEffect(magicNumber, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        } else {
            this.addToBot(new VFXAction(new BlizzardEffect(magicNumber, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        }
        for(int i = 0; i < magicNumber; ++i) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        baseMagicNumber = countTotalDebuffs();
        magicNumber = baseMagicNumber;
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        baseMagicNumber = countTotalDebuffs();
        magicNumber = baseMagicNumber;
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
}
