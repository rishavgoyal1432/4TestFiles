////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; eitherc
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that local, non-final variable names conform to a format specified
 * by the format property. A catch parameter is considered to be
 * a local variable. The format is a
 * {@link java.util.regex.Pattern regular expression}
 * and defaults to
 * <strong>^[a-z][a-zA-Z0-9]*$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with a lower
 * case letter, followed by letters, digits, and underscores is:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"&gt;
 *    &lt;property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @version 1.0
 */
public class LocalVariableNameCheck
    extends AbstractNameCheck
{
    /** Creates a new <code>LocalVariableNameCheck</code> instance. */
    public LocalVariableNameCheck()
    {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    /** {@inheritDoc} */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
        };
    }

    /** {@inheritDoc} */
    protected final boolean mustCheckName(DetailAST aAST)
    {
        final DetailAST modifiersAST =
            aAST.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isFinal = (modifiersAST != null)
            && modifiersAST.branchContains(TokenTypes.FINAL);
        return (!isFinal && ScopeUtils.isLocalVariableDef(aAST));
    }
}
