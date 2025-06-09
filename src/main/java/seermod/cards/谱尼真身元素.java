package seermod.cards;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import seermod.character.SEER;
import seermod.powers.谱尼元素;
import seermod.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class 谱尼真身元素 extends BaseCard{
    public static final String ID = makeID("True Pony Element");
    private static final int BASE_EXHAUSTIVE = 1;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public 谱尼真身元素() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
        addToBot(new SFXAction("ATTACK_FIRE"));
        addToBot(new VFXAction(p, new VerticalAuraEffect(Color.WHITE, p.hb.cX, p.hb.cY), 0.33F));
        addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.GOLDENROD), 0.0F, true));

        if (!player.hasPower(谱尼元素.POWER_ID)){
            addToBot(new ApplyPowerAction(p, p, new 谱尼元素(p)));
        }
    }

}
