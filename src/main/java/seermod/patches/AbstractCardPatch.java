package seermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import seermod.powers.机械飞升;

@SpirePatch(
    clz = AbstractCard.class,
    method = "canUse",
    paramtypez = {AbstractPlayer.class, AbstractMonster.class}
)
public class AbstractCardPatch {
    @SpirePostfixPatch
    public static boolean Postfix(boolean __result, AbstractCard __instance, AbstractPlayer p, AbstractMonster m) {
        if (__instance.type == AbstractCard.CardType.STATUS && __instance.costForTurn < -1 && p.hasPower(机械飞升.POWER_ID)) {
            return true;
        }
        return __result;
    }
} 